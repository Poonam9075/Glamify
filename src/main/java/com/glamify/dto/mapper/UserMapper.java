package com.glamify.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.glamify.dto.UserDto;
import com.glamify.entity.User;

public class UserMapper {

	public static UserDto toDto(User user)
	{
		UserDto userDto = new UserDto();
		
		userDto.setActive(user.isActive());
		userDto.setEmail(user.getEmail());
		userDto.setFullName(user.getFullName());
		userDto.setGender(user.getGender());
		userDto.setPassword(user.getPassword());
		userDto.setPhone(user.getPhone());
		userDto.setUserRole(user.getRole());
		userDto.setUserId(user.getUserId());
		
		return userDto;
	}
	
	public static List<UserDto> toDtolist(List<User> userList)
	{
		List<UserDto> userDtoList = new ArrayList<>();
		
		for(User user : userList)
		{
			userDtoList.add(UserMapper.toDto(user));
		}
		
		return userDtoList;
	}
}
