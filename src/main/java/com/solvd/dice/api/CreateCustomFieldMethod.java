package com.solvd.dice.api;

import com.solvd.dice.api.tcmCustomField.CustomField;
import com.zebrunner.carina.api.AbstractApiMethodV2;
import com.zebrunner.carina.api.annotation.Endpoint;
import com.zebrunner.carina.api.annotation.RequestTemplatePath;
import com.zebrunner.carina.api.http.HttpMethodType;

import static com.solvd.dice.api.service.TcmService.valueAsString;

@Endpoint(url = "https://solvdinternal.zebrunner.com/api/tcm/v1/test-case-settings/custom-fields?projectId=42", methodType = HttpMethodType.POST)
@RequestTemplatePath(path = "api/post/postCustomField.json")
public class CreateCustomFieldMethod extends AbstractApiMethodV2 {

    public void CreateCustomFieldMethod(String token, CustomField customField) {
        setHeaders("Authorization=Bearer " + token);
        addProperty("tabId", customField.getTabId());
        addProperty("name", customField.getName());
        addProperty("dataType", customField.getDataType());
        addProperty("value", valueAsString);
    }

}
