import axios from "axios";

export const api= axios.create({
    baseURL:"http://localhost:9999"
})

export const getHeader = () => {
	return {
		"Content-Type": "application/json"
	}
}

// add room
export async function addRoom(roomNum, roomType, totalPerson, price, isAvailable, photo) {
  const payload = {
      roomNum: roomNum,
      roomType: roomType,
      totalPerson: totalPerson,
      price: price,
      isAvailable: isAvailable,
      photo: photo
  };

  try {
      const response = await api.post("/api/rooms", payload, {
          headers: { "Content-Type": "application/json" }
      });
      if (response.status === 200) {
          return true;
      } else {
          console.error(`Error: Unexpected status code ${response.status}`);
          return false;
      }
  } catch (error) {
      console.error("Error adding room:", error);
      return false;
  }
}
// static data
export function getRoomType() {
  // Static list of room types
  const roomTypes = ['Single', 'Double', 'Deluxe', 'Luxury', 'Suite'];
  return roomTypes;
}

// get all rooms
export async function getAllRooms() {
  try {
    const result = await api.get("/api/rooms");

    
    console.log("Raw API Response:", result);


    return Array.isArray(result.data) ? result.data : [];
  } catch (error) {
    console.error("Error fetching rooms:", error.response ? error.response.data : error.message);
    throw new Error("Error fetching rooms. Please try again later.");
  }
}


//to delete room by id

export async function deleteRoom(id) {
  try {
    const result = await api.delete(`/api/rooms/${id}`);
    return result.data;
  } catch (error) {
    throw new Error(`Error deleting room: ${error.message}`);
  }
}

// update room by Id
export async function updateRoom(id, roomData) {
  const payload = {
      roomNum: roomData.roomNum,
      roomType: roomData.roomType,
      totalPerson: roomData.totalPerson,
      price: roomData.price,
      isAvailable: roomData.isAvailable,
      photo: roomData.photo
  };
  try {
      const response = await api.put(`/api/rooms/${id}`, payload, {
          headers: { "Content-Type": "application/json" }
      });
      if (response.status === 200) {
          return response;
      } else {
          console.error(`Error: Unexpected status code ${response.status}`);
          return null;
      }
  } catch (error) {
      console.error("Error updating room:", error);
      return null;
  }
}


 //get room by id
export async function getRoomById(id) {
  try{
    const result=await api.get(`/api/rooms/${id}`)
    return result.data
  }catch(error){
throw new Error('Error fetching room ${error.message}')
  }
  
}

export async function bookRoom(booking) {
  try {
    const response = await api.post(`/api/bookings`, booking);
    return response.data;
  } catch (error) {
    console.error("Error booking room:", error.response ? error.response.data : error.message);
    throw error;
  }
}

//to get all bookings
export async function getAllBookings() {
	try {
		const result = await api.get("api/bookings", {
			headers: getHeader()
		})
		return result.data
	} catch (error) {
		throw new Error(`Error fetching bookings : ${error.message}`)
	}
}
//get booking by the cnfirmation code 

export async function getBookingByUsername(username) {
	try {
		const result = await api.get(`api/bookings/full-booking-details/${username}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking : ${error.message}`)
		}
	}
}

//to cancel user booking
export async function cancelBooking(id) {
	try {
		const result = await api.delete(`api/bookings/${id}`)
		return result.data
	} catch (error) {
		throw new Error(`Error cancelling booking :${error.message}`)
	}
}

//gets all availavle rooms from the database with a given date and a room type

export async function getAvailableRooms(roomType){
  try {
		const result = await api.get(`api/rooms/roomType/${roomType}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error finding room : ${error.message}`)
		}
	}
  
}