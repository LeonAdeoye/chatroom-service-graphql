package com.leon.services;

import com.leon.models.Room;
import java.util.List;

public interface RoomService
{
	List<Room> getRooms();
	Room getRoomById(String id);
}
