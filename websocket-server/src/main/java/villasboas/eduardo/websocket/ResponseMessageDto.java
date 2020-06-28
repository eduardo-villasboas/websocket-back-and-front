package villasboas.eduardo.websocket;

public class ResponseMessageDto {

	private String content;

	public ResponseMessageDto() {}
	
	public ResponseMessageDto(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
