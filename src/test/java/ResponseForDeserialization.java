import com.fasterxml.jackson.annotation.JsonProperty;
import deserialization.ResponseForDeserializationItem;

import java.util.List;

public class ResponseForDeserialization{

	@JsonProperty("ResponseForDeserialization")
	private List<ResponseForDeserializationItem> responseForDeserialization;

	public List<ResponseForDeserializationItem> getResponseForDeserialization(){
		return responseForDeserialization;
	}
}
