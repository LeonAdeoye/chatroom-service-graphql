package com.leon.services;

import com.leon.models.Room;
import com.leon.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService
{
	private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);
	private Map<UUID, Room> roomsMap;

	@Autowired
	RoomRepository roomRepository;

	@PostConstruct
	public void initialize()
	{
		loadFromStore();
	}

	private void loadFromStore()
	{
		List<Room> loadedRooms = roomRepository.findAll();
		logger.info("Loaded " + loadedRooms.size() + " room(s) from store.");

		if(loadedRooms.size() > 0)
			roomsMap = loadedRooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
		else
			roomsMap = new HashMap<>();
	}

	@Override
	public List<Room> getRooms()
	{
		return this.roomsMap.values().stream().collect(Collectors.toList());
	}

	public Room getRoomById(String id)
	{
		UUID key = UUID.fromString(id);
		if(roomsMap.containsKey(key))
			return roomsMap.get(key);
		else
			return null;
	}
}
