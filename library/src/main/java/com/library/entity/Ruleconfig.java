package com.library.entity;

public class Ruleconfig {
    private Integer id;
    private String ruleName;
    private String ruleValue;
    private String intro;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getRuleValue() { return ruleValue; }
    public void setRuleValue(String ruleValue) { this.ruleValue = ruleValue; }
    public String getIntro() { return intro; }
    public void setIntro(String intro) { this.intro = intro; }
}