package com.example.demo3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class csvReaderData {
    private static final DateTimeFormatter[] DATE_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("M/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM/d/yyyy"),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("MM/d/yyyy"),
            DateTimeFormatter.ofPattern("M/dd/yyyy"),
    };

    public static List<EmployeeProject> read(String filePath) throws IOException {
        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                int employeeId = Integer.parseInt(tokens[0].trim());
                int projectId = Integer.parseInt(tokens[1].trim());
                LocalDate dateFrom = parseDate(tokens[2].trim());
                LocalDate dateTo = parseDate(tokens[3].trim());
                employeeProjects.add(new EmployeeProject(employeeId, projectId, dateFrom, dateTo));
            }
        }

        return employeeProjects;
    }

    private static LocalDate parseDate(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
            }
        }
        return LocalDate.now();
    }
}
