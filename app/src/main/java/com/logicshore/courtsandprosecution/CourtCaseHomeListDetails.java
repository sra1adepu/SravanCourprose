package com.logicshore.courtsandprosecution;

import java.util.ArrayList;

/**
 * Created by LENOVO on 31-10-2017.
 */

public class CourtCaseHomeListDetails {
    String crime_number;
    String courtcase_number, charge_sheet_number, charge_sheet_dt, court_name;
    ArrayList<String> caseStages_arraylist;
    String adjumenentno,stageofthe_case,nextadjence_dt,action;
    String courtcase_sr_number;

    public String getTrialserialno() {
        return trialserialno;
    }

    public void setTrialserialno(String trialserialno) {
        this.trialserialno = trialserialno;
    }

    String trialserialno;

    public String getFir_reg_number() {
        return fir_reg_number;
    }

    public void setFir_reg_number(String fir_reg_number) {
        this.fir_reg_number = fir_reg_number;
    }

    String fir_reg_number;

    public String getCourtcase_sr_number() {
        return courtcase_sr_number;
    }

    public void setCourtcase_sr_number(String courtcase_sr_number) {
        this.courtcase_sr_number = courtcase_sr_number;
    }

    public String getPolicestation() {
        return policestation;
    }

    public void setPolicestation(String policestation) {
        this.policestation = policestation;
    }

    public String getSdpo_name() {
        return sdpo_name;
    }

    public void setSdpo_name(String sdpo_name) {
        this.sdpo_name = sdpo_name;
    }

    String policestation;
    String sdpo_name;

    public String getStageofthe_case() {
        return stageofthe_case;
    }

    public void setStageofthe_case(String stageofthe_case) {
        this.stageofthe_case = stageofthe_case;
    }

    public String getNextadjence_dt() {
        return nextadjence_dt;
    }

    public void setNextadjence_dt(String nextadjence_dt) {
        this.nextadjence_dt = nextadjence_dt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAdjumenentno() {
        return adjumenentno;
    }

    public void setAdjumenentno(String adjumenentno) {
        this.adjumenentno = adjumenentno;
    }

    public ArrayList<String> getCaseStages_arraylist() {
        return caseStages_arraylist;
    }

    public void setCaseStages_arraylist(ArrayList<String> caseStages_arraylist) {
        this.caseStages_arraylist = caseStages_arraylist;
    }

    public String getCrime_number() {
        return crime_number;
    }

    public void setCrime_number(String crime_number) {
        this.crime_number = crime_number;
    }

    public String getCourtcase_number() {
        return courtcase_number;
    }

    public void setCourtcase_number(String courtcase_number) {
        this.courtcase_number = courtcase_number;
    }

    public String getCharge_sheet_number() {
        return charge_sheet_number;
    }

    public void setCharge_sheet_number(String charge_sheet_number) {
        this.charge_sheet_number = charge_sheet_number;
    }

    public String getCharge_sheet_dt() {
        return charge_sheet_dt;
    }

    public void setCharge_sheet_dt(String charge_sheet_dt) {
        this.charge_sheet_dt = charge_sheet_dt;
    }

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }


}