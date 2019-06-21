package com.wxss.freemarkerlearn;

import java.util.Arrays;

/**
 * Author:Created by wx on 2019/6/18
 * Desc:
 */
public class VaccineImmunizationInChildData {
    private String vaccineName; // 疫苗名称
    private String abbreviation; // 疫苗缩写
    private String[] vaccinationTime; // 接种时间

    public VaccineImmunizationInChildData() {
    }

    public VaccineImmunizationInChildData(String vaccineName, String abbreviation, String[] vaccinationTime) {
        this.vaccineName = vaccineName;
        this.abbreviation = abbreviation;
        this.vaccinationTime = vaccinationTime;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String[] getVaccinationTime() {
        return vaccinationTime;
    }

    public void setVaccinationTime(String[] vaccinationTime) {
        this.vaccinationTime = vaccinationTime;
    }

    @Override
    public String toString() {
        return "VaccineImmunizationInChildData{" +
                "vaccineName='" + vaccineName + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", vaccinationTime=" + Arrays.toString(vaccinationTime) +
                '}';
    }
}
