package bidding.example.com.bidding.GetterSetter;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by Sandesh on 22-Nov-15.
 */
public class ModelClass
{
    String ApiUrl;
    HashMap<String,String> parameter;
    HashMap<String,Integer> apiParameter;
    JSONArray array;

    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }


    public HashMap<String, Integer> getApiParameter() {
        return apiParameter;
    }

    public void setApiParameter(HashMap<String, Integer> apiParameter) {
        this.apiParameter = apiParameter;
    }



    public String getApiUrl() {
        return ApiUrl;
    }

    public void setApiUrl(String apiUrl) {
        ApiUrl = apiUrl;
    }

    public HashMap<String, String> getParameter() {
        return parameter;
    }

    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }
}
