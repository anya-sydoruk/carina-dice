package com.solvd.dice.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TestRailTestCasePojo {

    public Long id;
    public String title;
    public int section_id;
    public int template_id;
    public int type_id;
    public int priority_id;
    public int milestone_id;
    public String refs;
    public int created_by;
    public Date created_on;
    public int updated_by;
    public Date updated_on;
    public String estimate;
    public String estimate_forecast;
    public int suite_id;
    public int display_order;
    public int is_deleted;
    public int custom_automation_type;
    public String custom_preconds;
    public String custom_steps;
    public String custom_testrail_bdd_scenario;
    public String custom_expected;
    public String custom_steps_separated;
    public String custom_mission;
    public String custom_goals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(int priority_id) {
        this.priority_id = priority_id;
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getRefs() {
        return refs;
    }

    public void setRefs(String refs) {
        this.refs = refs;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getEstimate_forecast() {
        return estimate_forecast;
    }

    public void setEstimate_forecast(String estimate_forecast) {
        this.estimate_forecast = estimate_forecast;
    }

    public int getSuite_id() {
        return suite_id;
    }

    public void setSuite_id(int suite_id) {
        this.suite_id = suite_id;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getCustom_automation_type() {
        return custom_automation_type;
    }

    public void setCustom_automation_type(int custom_automation_type) {
        this.custom_automation_type = custom_automation_type;
    }

    public String getCustom_preconds() {
        return custom_preconds;
    }

    public void setCustom_preconds(String custom_preconds) {
        this.custom_preconds = custom_preconds;
    }

    public String getCustom_steps() {
        return custom_steps;
    }

    public void setCustom_steps(String custom_steps) {
        this.custom_steps = custom_steps;
    }

    public String getCustom_testrail_bdd_scenario() {
        return custom_testrail_bdd_scenario;
    }

    public void setCustom_testrail_bdd_scenario(String custom_testrail_bdd_scenario) {
        this.custom_testrail_bdd_scenario = custom_testrail_bdd_scenario;
    }

    public String getCustom_expected() {
        return custom_expected;
    }

    public void setCustom_expected(String custom_expected) {
        this.custom_expected = custom_expected;
    }

    public String getCustom_steps_separated() {
        return custom_steps_separated;
    }

    public void setCustom_steps_separated(String custom_steps_separated) {
        this.custom_steps_separated = custom_steps_separated;
    }

    public String getCustom_mission() {
        return custom_mission;
    }

    public void setCustom_mission(String custom_mission) {
        this.custom_mission = custom_mission;
    }

    public String getCustom_goals() {
        return custom_goals;
    }

    public void setCustom_goals(String custom_goals) {
        this.custom_goals = custom_goals;
    }

    @JsonCreator
    public TestRailTestCasePojo(@JsonProperty("id") Long id,@JsonProperty("title") String title,@JsonProperty("section_id") int section_id,@JsonProperty("template_id") int template_id,@JsonProperty("type_id") int type_id,@JsonProperty("priority_id") int priority_id,@JsonProperty("milestone_id") int milestone_id,@JsonProperty("refs") String refs,@JsonProperty("created_by") int created_by,@JsonProperty("created_on") Date created_on,@JsonProperty("updated_by") int updated_by,@JsonProperty("updated_on") Date updated_on,@JsonProperty("estimate") String estimate,@JsonProperty("estimate_forecast") String estimate_forecast,@JsonProperty("suite_id") int suite_id,@JsonProperty("display_order") int display_order,@JsonProperty("is_deleted") int is_deleted,@JsonProperty("custom_automation_type") int custom_automation_type,@JsonProperty("custom_preconds") String custom_preconds,@JsonProperty("custom_steps") String custom_steps,@JsonProperty("custom_testrail_bdd_scenario") String custom_testrail_bdd_scenario,@JsonProperty("custom_expected") String custom_expected,@JsonProperty("custom_steps_separated") String custom_steps_separated,@JsonProperty("custom mission") String custom_mission,@JsonProperty("custom_goals") String custom_goals) {
        this.id = id;
        this.title = title;
        this.section_id = section_id;
        this.template_id = template_id;
        this.type_id = type_id;
        this.priority_id = priority_id;
        this.milestone_id = milestone_id;
        this.refs = refs;
        this.created_by = created_by;
        this.created_on = created_on;
        this.updated_by = updated_by;
        this.updated_on = updated_on;
        this.estimate = estimate;
        this.estimate_forecast = estimate_forecast;
        this.suite_id = suite_id;
        this.display_order = display_order;
        this.is_deleted = is_deleted;
        this.custom_automation_type = custom_automation_type;
        this.custom_preconds = custom_preconds;
        this.custom_steps = custom_steps;
        this.custom_testrail_bdd_scenario = custom_testrail_bdd_scenario;
        this.custom_expected = custom_expected;
        this.custom_steps_separated = custom_steps_separated;
        this.custom_mission = custom_mission;
        this.custom_goals = custom_goals;
    }

    public TestRailTestCasePojo(){}
}
