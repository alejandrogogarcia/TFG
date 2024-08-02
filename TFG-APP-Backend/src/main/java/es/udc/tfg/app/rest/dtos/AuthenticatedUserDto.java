package es.udc.tfg.app.rest.dtos;

public class AuthenticatedUserDto {
	
	private String token;
	private UserDto userDto;

public AuthenticatedUserDto() {}
	
	public AuthenticatedUserDto(String token, UserDto userDto) {
		
		this.token = token;
		this.userDto = userDto;
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}
