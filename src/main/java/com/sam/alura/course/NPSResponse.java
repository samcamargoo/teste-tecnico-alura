package com.sam.alura.course;

public record NPSResponse(String courseName,
                          long nps,
                          long promoters,
                          long detractors) {
}
