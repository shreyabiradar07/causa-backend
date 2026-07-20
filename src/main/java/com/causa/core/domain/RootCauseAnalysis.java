package com.causa.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Root Cause Analysis Domain Model
 *
 * <p>Represents the structured output from the LLM-based RCA process.
 * This matches the JSON schema defined in {@code rca-prompt-template.yml}.
 *
 * @since 0.0.1
 */
public record RootCauseAnalysis(
    @JsonProperty("issue_title")
    String issueTitle,

    @JsonProperty("issue_summary")
    String issueSummary,

    @JsonProperty("issue_description")
    String issueDescription,

    @JsonProperty("technical_description")
    String technicalDescription,

    @JsonProperty("anomaly_type")
    AnomalyType anomalyType,

    @JsonProperty("root_cause")
    String rootCause,

    @JsonProperty("supporting_logs")
    List<String> supportingLogs,

    @JsonProperty("evidences")
    List<String> evidences,

    @JsonProperty("recommendations")
    List<Recommendation> recommendations,

    @JsonProperty("confidence_summary")
    ConfidenceSummary confidenceSummary,

    @JsonProperty("llm_notes")
    String llmNotes
) {

    /**
     * Anomaly Type Classification
     */
    public enum AnomalyType {
        @JsonProperty("OOM_KILLED")
        OOM_KILLED,

        @JsonProperty("POSSIBLE_OOM_KILLED")
        POSSIBLE_OOM_KILLED,

        @JsonProperty("POSSIBLE_GC_PAUSE")
        POSSIBLE_GC_PAUSE,

        @JsonProperty("HEALTHY")
        HEALTHY
    }

    /**
     * Confidence summary — wraps the RCA confidence score and explanation text.
     */
    public record ConfidenceSummary(
        @JsonProperty("rca_confidence_score")
        @NotNull(message = "RCA confidence score is required")
        @DecimalMin(value = "0.0", message = "RCA confidence score must be >= 0.0")
        @DecimalMax(value = "1.0", message = "RCA confidence score must be <= 1.0")
        Double rcaConfidenceScore,

        @JsonProperty("summary_text")
        String summaryText
    ) {}

    /**
     * Recommendation record matching the prompt's recommendations array structure.
     */
    public record Recommendation(
        @JsonProperty("solution_type")
        String solutionType,

        @JsonProperty("solution_title")
        String solutionTitle,

        @JsonProperty("solution_description")
        String solutionDescription,

        @JsonProperty("implementation_notes")
        String implementationNotes,

        @JsonProperty("solution_confidence_score")
        @DecimalMin(value = "0.0", message = "Solution confidence score must be >= 0.0")
        @DecimalMax(value = "1.0", message = "Solution confidence score must be <= 1.0")
        Double solutionConfidenceScore,

        @JsonProperty("solution_alerts")
        List<String> solutionAlerts
    ) {}
}
