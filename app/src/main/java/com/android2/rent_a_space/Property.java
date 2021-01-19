package com.android2.rent_a_space;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Property {

    //basic entry
    private String propertyADDRESSHOUSENUMBER;
    private String propertyADDRESSSTREET;
    private String propertyADDRESSSBRGY;
    private String propertyADDRESSSCITY;
    private String propertyADDRESSSAREA;
    private String propertyLANDMARK;
    private @ServerTimestamp
    Date timestamp;
    private String user_id;
    private String property_id;
    private String property_ImageUri;



    //owner Details entry
    private String propertyOWNER;
    private String propertyOWNERMOBILE;
    private String propertyOWNERFB;

    //accommodation
    private String propertyGENDERACCEPT;
    //price of property
    private String propertyPRICE;
    private String per;
    private String propertyADVANCE;
    private String propertyDEPOSIT;

    //property status
    private String property_status;

    public Property() {
        // no args constructor
    }


    public Property(String propertyADDRESSHOUSENUMBER, String propertyADDRESSSTREET, String propertyADDRESSSBRGY,
                    String propertyADDRESSSCITY, String propertyADDRESSSAREA,
                    String propertyLANDMARK, Date timestamp, String property_id,
                    String user_id, String property_ImageUri, String propertyOWNER, String propertyOWNERMOBILE,
                    String propertyOWNERFB, String propertyGENDERACCEPT, String propertyPRICE,String per,
                    String propertyADVANCE, String propertyDEPOSIT,String property_status) {

        this.property_status = property_status;
        this.propertyOWNER = propertyOWNER;
        this.propertyADDRESSHOUSENUMBER = propertyADDRESSHOUSENUMBER;
        this.propertyADDRESSSTREET = propertyADDRESSSTREET;
        this.propertyADDRESSSBRGY = propertyADDRESSSBRGY;
        this.propertyADDRESSSCITY = propertyADDRESSSCITY;
        this.propertyADDRESSSAREA = propertyADDRESSSAREA;
        this.propertyLANDMARK = propertyLANDMARK;
        this.timestamp = timestamp;
        this.property_id = property_id;
        this.user_id = user_id;
        this.property_ImageUri = property_ImageUri;
        this.propertyOWNERMOBILE = propertyOWNERMOBILE;
        this.propertyOWNERFB = propertyOWNERFB;
        this.propertyGENDERACCEPT = propertyGENDERACCEPT;
        this.propertyPRICE = propertyPRICE;
        this.propertyADVANCE = propertyADVANCE;
        this.propertyDEPOSIT = propertyDEPOSIT;
        this.per = per;

    }

    public String getProperty_status() {
        return property_status;
    }

    public void setProperty_status(String property_status) {
        this.property_status = property_status;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getPropertyDEPOSIT() {
        return propertyDEPOSIT;
    }

    public void setPropertyDEPOSIT(String propertyDEPOSIT) {
        this.propertyDEPOSIT = propertyDEPOSIT;
    }

    public String getPropertyADVANCE() {
        return propertyADVANCE;
    }

    public void setPropertyADVANCE(String propertyADVANCE) {
        this.propertyADVANCE = propertyADVANCE;
    }


    public String getPropertyPRICE() {
        return propertyPRICE;
    }

    public void setPropertyPRICE(String propertyPRICE) {
        this.propertyPRICE = propertyPRICE;
    }

    public String getPropertyGENDERACCEPT() {
        return propertyGENDERACCEPT;
    }

    public void setPropertyGENDERACCEPT(String propertyGENDERACCEPT) {
        this.propertyGENDERACCEPT = propertyGENDERACCEPT;
    }

    public String getPropertyOWNERFB() {
        return propertyOWNERFB;
    }

    public void setPropertyOWNERFB(String propertyOWNERFB) {
        this.propertyOWNERFB = propertyOWNERFB;
    }

    public String getPropertyOWNERMOBILE() {
        return propertyOWNERMOBILE;
    }

    public void setPropertyOWNERMOBILE(String propertyOWNERMOBILE) {
        this.propertyOWNERMOBILE = propertyOWNERMOBILE;
    }

    public String getPropertyOWNER() {
        return propertyOWNER;
    }

    public void setPropertyOWNER(String propertyOWNER) {
        this.propertyOWNER = propertyOWNER;
    }

    public String getPropertyADDRESSHOUSENUMBER() {
        return propertyADDRESSHOUSENUMBER;
    }

    public void setPropertyADDRESSHOUSENUMBER(String propertyADDRESSHOUSENUMBER) {
        this.propertyADDRESSHOUSENUMBER = propertyADDRESSHOUSENUMBER;
    }

    public String getPropertyADDRESSSTREET() {
        return propertyADDRESSSTREET;
    }

    public void setPropertyADDRESSSTREET(String propertyADDRESSSTREET) {
        this.propertyADDRESSSTREET = propertyADDRESSSTREET;
    }

    public String getPropertyADDRESSSBRGY() {
        return propertyADDRESSSBRGY;
    }

    public void setPropertyADDRESSSBRGY(String propertyADDRESSSBRGY) {
        this.propertyADDRESSSBRGY = propertyADDRESSSBRGY;
    }

    public String getPropertyADDRESSSCITY() {
        return propertyADDRESSSCITY;
    }

    public void setPropertyADDRESSSCITY(String propertyADDRESSSCITY) {
        this.propertyADDRESSSCITY = propertyADDRESSSCITY;
    }

    public String getPropertyADDRESSSAREA() {
        return propertyADDRESSSAREA;
    }

    public void setPropertyADDRESSSAREA(String propertyADDRESSSAREA) {
        this.propertyADDRESSSAREA = propertyADDRESSSAREA;
    }

    public String getPropertyLANDMARK() {
        return propertyLANDMARK;
    }

    public void setPropertyLANDMARK(String propertyLANDMARK) {
        this.propertyLANDMARK = propertyLANDMARK;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProperty_ImageUri() {
        return property_ImageUri;
    }

    public void setProperty_ImageUri(String property_ImageUri) {
        this.property_ImageUri = property_ImageUri;
    }
}
