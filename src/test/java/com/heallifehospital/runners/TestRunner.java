package com.heallifehospital.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Configuration;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;


@RunWith(Cucumber.class)
@CucumberOptions(
        // Test sonuçlarının raporlanmasını sağlayan Cucumber ayarlarI
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/rerun.txt"
        },
        // Hangi senaryoların çalıştırılacağını belirten etiket
        tags ="@test",
        features ="src/test/java/com/heallifehospital/features",
        glue  = "com/heallifehospital/stepDefs",
        dryRun = false
)
public class TestRunner {
    @AfterClass
    public static void teardown() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        generateReport(reportOutputDirectory.getAbsolutePath());
    }
        // Cucumber raporlarını üreten metot
        public static void generateReport(String cucumberOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(cucumberOutputPath), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));

        // HTML raporlarını oluşturur ve hedef dizine kaydeder
        Configuration config = new Configuration(new File("target"), "project-team2");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}


