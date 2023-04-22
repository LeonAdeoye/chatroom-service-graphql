package com.leon.controllers;

import com.leon.models.ChatMessage;
import com.leon.models.Room;
import com.leon.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Controller
public class MainController
{
	@Autowired
	RoomService roomService;

	/*
	http://localhost:8080/graphiql/?path=/graphql
	query {
	  rooms {
		id
		isValid
		isPrivate
		roomName
		members
		activities {
		  instigatorId
		  activity
		  thirdPartyId
		}
		administrators
		conversation {
		  id
		  content
		  authorId
		}
		ownerId
	  }
	}
	*/
	@SchemaMapping(typeName = "Query", field = "rooms")
	Flux<Room> getAllRooms()
	{
		return Flux.fromIterable(roomService.getRooms());
	}

	@QueryMapping
	Mono<Room> getRoomById(@Argument String id)
	{
		return Mono.just(roomService.getRoomById(id));
	}

	/*
	http://localhost:8080/graphiql/?path=/graphql
	query {
		conversationByRoomId(id: "1efa4f20-38ab-4fb9-85e7-5201095af9a2") {
			id
			content
			authorId
		}
	}
	*/
	@SchemaMapping(typeName = "Query", field = "conversationByRoomId")
	List<ChatMessage> getConversationByRoomId(@Argument String id)
	{
		Room room = roomService.getRoomById(id);
		return room.getConversation();
	}
}
