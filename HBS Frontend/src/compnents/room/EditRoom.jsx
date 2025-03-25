import React, { useEffect, useState } from 'react';
import { getRoomById, updateRoom } from '../utils/ApiFunctions';
import { useParams, useNavigate } from 'react-router-dom';
import RoomTypeSelector from '../common/RoomTypeSelector';

const EditRoom = () => {
  const [room, setRoom] = useState({
    roomNum: '',
    photo: '',
    roomType: '',
    totalPerson: '',
    roomPrice: '',
    isAvailable: true,
  });

  const [imagePreview, setImagePreview] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const { id } = useParams();
  const navigate = useNavigate();

  const handleRoomInputChange = (e) => {
    const { name, value } = e.target;
    setRoom((prevRoom) => ({
      ...prevRoom,
      [name]: name === 'roomPrice' || name === 'totalPerson' ? parseInt(value) : value,
    }));
  };

  const handleToggleAvailability = () => {
    setRoom((prevRoom) => ({
      ...prevRoom,
      isAvailable: !prevRoom.isAvailable,
    }));
  };

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const roomData = await getRoomById(id);
        setRoom(roomData);
        setImagePreview(roomData.photo);
      } catch (error) {
        console.error('Error fetching room:', error);
      }
    };
    fetchRoom();
  }, [id]);

  useEffect(() => {
    setImagePreview(room.photo);
  }, [room.photo]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await updateRoom(id, {
        ...room,
        price: room.roomPrice, // Map `roomPrice` to `price` for the API
      });

      if (response) {
        setSuccessMessage('Room updated successfully.');
        const updatedRoomData = await getRoomById(id);
        setRoom(updatedRoomData);
        setImagePreview(updatedRoomData.photo);
        setErrorMessage('');
      } else {
        setErrorMessage('Error updating room.');
      }
    } catch (error) {
      console.error('Error updating room:', error);
      setErrorMessage(error.message);
    }
  };

  const handleBack = () => {
    navigate('/existing-rooms');
  };

  return (
    <section className="container mt-5 mb-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <h2 className="mt-5 mb-2">Edit Room</h2>
          {successMessage && (
            <div className="alert alert-success fade show">{successMessage}</div>
          )}
          {errorMessage && (
            <div className="alert alert-danger fade show">{errorMessage}</div>
          )}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="roomNum" className="form-label">Room Number</label>
              <input
                className="form-control"
                required
                id="roomNum"
                name="roomNum"
                type="text"
                value={room.roomNum}
                onChange={handleRoomInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="roomType" className="form-label">Room Type</label>
              <RoomTypeSelector newRoom={room} handleRoomInputChange={handleRoomInputChange} />
            </div>

            <div className="mb-3">
              <label htmlFor="totalPerson" className="form-label">Total Person Capacity</label>
              <input
                className="form-control"
                required
                id="totalPerson"
                name="totalPerson"
                type="number"
                value={room.totalPerson}
                onChange={handleRoomInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="roomPrice" className="form-label">Room Price</label>
              <input
                className="form-control"
                required
                id="roomPrice"
                name="roomPrice"
                type="number"
                value={room.roomPrice}
                onChange={handleRoomInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="isAvailable" className="form-label">Availability</label>
              <button
                type="button"
                className={`btn btn-${room.isAvailable ? 'success' : 'danger'}`}
                onClick={handleToggleAvailability}
              >
                {room.isAvailable ? 'Available' : 'Not Available'}
              </button>
            </div>

            <div className="mb-3">
              <label htmlFor="photo" className="form-label">Image URL</label>
              <input
                className="form-control"
                required
                id="photo"
                name="photo"
                type="text"
                value={room.photo}
                onChange={handleRoomInputChange}
              />
            </div>

            {imagePreview && (
              <img
                src={imagePreview}
                alt="Preview room photo"
                style={{ maxWidth: '400px', maxHeight: '400px' }}
                className="mb-3"
              />
            )}

            <div className="d-grid justify-content-between d-md-flex mt-2">
              <button type="button" className="btn btn-outline-primary ml-5" onClick={handleBack}>
                Back
              </button>
              <button className="btn btn-outline-primary ml-5">Edit Room</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default EditRoom;
