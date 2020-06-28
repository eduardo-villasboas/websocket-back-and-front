package villasboas.eduardo.websocket;

public class RequestMessageDto {

	private String name;

	public RequestMessageDto() {
	}

	public RequestMessageDto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
