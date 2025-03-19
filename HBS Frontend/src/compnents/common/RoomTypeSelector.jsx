import React, { useEffect, useState } from 'react';
import { getRoomType } from '../utils/ApiFunctions';

const RoomTypeSelector = ({ handleRoomInputChange, newRoom }) => {
  const [roomTypes, setRoomTypes] = useState(['Single', 'Double', 'Delux', 'Luxury', 'Suite']);

  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const data = getRoomType();
        setRoomTypes(data);
      } catch (error) {
        console.error("Error fetching room types:", error);
      }
    };

    fetchRoomTypes();
  }, []);

  return (
    <>
      {roomTypes.length > 0 && (
        <div>
          <select
            id="roomType"
            name="roomType"
            value={newRoom?.roomType || ''}
            onChange={handleRoomInputChange}
            className="form-select"
          >
            <option value="">Select a room type</option>
            {roomTypes.map((type, index) => (
              <option key={index} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
      )}
    </>
  );
};

export default RoomTypeSelector;
