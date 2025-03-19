import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const RoomFilter = ({ data, setFilteredData }) => {
  const [filter, setFilter] = useState("");

  const handleSelectChange = (e) => {
    const selectedRoomType = e.target.value;
    setFilter(selectedRoomType);
    if (selectedRoomType === "") {
      // Reset filter to display all rooms
      setFilteredData(data);
    } else {
      // Filter rooms by roomType
      const filteredRooms = data.filter((room) =>
        room.roomType.toLowerCase() === selectedRoomType.toLowerCase()
      );
      setFilteredData(filteredRooms);
    }
  };

  const clearFilter = () => {
    setFilter("");
    setFilteredData(data);
  };

  // Extract unique room types from data and create filter options
  const roomTypes = ["", ...new Set(data.map((room) => room.roomType))];

  return (
    <div className="input-group mb-3">
      <span className="input-group-text" id="room-type-filter">Filter by Room Type</span>
      <select
        className="form-select"
        value={filter}
        onChange={handleSelectChange}
        aria-label="Room type filter"
      >
        <option value="">All Room Types</option>
        {roomTypes.map((type, index) => (
          <option key={index} value={type}>{type}</option>
        ))}
      </select>
      <button
        className="btn btn-outline-secondary"
        type="button"
        onClick={clearFilter}
      >
        Clear
      </button>
    </div>
  );
};

export default RoomFilter;
