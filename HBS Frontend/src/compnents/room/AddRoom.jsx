import React, { useState } from 'react';
import { addRoom } from '../utils/ApiFunctions';
import RoomTypeSelector from '../common/RoomTypeSelector';
import { useNavigate } from 'react-router-dom';

const AddRoom = () => {
  const [newRoom, setNewRoom] = useState({
    roomNum: '',
    photo: '',
    roomType: '',
    totalPerson: '',
    roomPrice: '',
    isAvailable: true, // Default to true
  });

  const [imagePreview, setImagePreview] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleRoomInputChange = (e) => {
    const { name, value } = e.target;
    setNewRoom((prevRoom) => ({
      ...prevRoom,
      [name]: value,
    }));
  };

  const handleToggleAvailability = () => {
    setNewRoom((prevRoom) => ({
      ...prevRoom,
      isAvailable: !prevRoom.isAvailable,
    }));
  };

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setNewRoom((prevRoom) => ({ ...prevRoom, photo: selectedImage.name })); // Using file name as photo URL simulation
    setImagePreview(URL.createObjectURL(selectedImage));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const success = await addRoom(
        newRoom.roomNum,
        newRoom.roomType,
        newRoom.totalPerson,
        newRoom.roomPrice,
        newRoom.isAvailable,
        newRoom.photo
      );

      if (success) {
        setSuccessMessage('New Room was added successfully');
        setNewRoom({
          roomNum: '',
          photo: '',
          roomType: '',
          totalPerson: '',
          roomPrice: '',
          isAvailable: true,
        });
        setImagePreview('');
        setErrorMessage('');
      } else {
        setErrorMessage('Error Adding Room');
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage('');
      setErrorMessage('');
    }, 3000);
  };

  const handleBack = () => {
    navigate('/existing-rooms');
  };

  return (
    <section className="container mt-5 mb-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <h2 className="mt-5 mb-2">Add a New Room</h2>
          {successMessage && <div className="alert alert-success fade show">{successMessage}</div>}
          {errorMessage && <div className="alert alert-danger fade show">{errorMessage}</div>}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="roomNum" className="form-label">Room Number</label>
              <input
                className="form-control"
                required
                id="roomNum"
                name="roomNum"
                type="text"
                value={newRoom.roomNum}
                onChange={handleRoomInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="roomType" className="form-label">Room Type</label>
              <div>
                <RoomTypeSelector newRoom={newRoom} handleRoomInputChange={handleRoomInputChange} />
              </div>
            </div>

            <div className="mb-3">
              <label htmlFor="totalPerson" className="form-label">Total Person Capacity</label>
              <input
                className="form-control"
                required
                id="totalPerson"
                name="totalPerson"
                type="number"
                value={newRoom.totalPerson}
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
                value={newRoom.roomPrice}
                onChange={handleRoomInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="isAvailable" className="form-label">Availability</label>
              <div>
                <button
                  type="button"
                  className={`btn btn-${newRoom.isAvailable ? 'success' : 'danger'}`}
                  onClick={handleToggleAvailability}
                >
                  {newRoom.isAvailable ? 'Available' : 'Not Available'}
                </button>
              </div>
            </div>

            <div className="mb-3">
              <label htmlFor="photo" className="form-label">Photo</label>
              <input
                className="form-control"
                required
                id="photo"
                name="photo"
                type="file"
                onChange={handleImageChange}
              />
              {imagePreview && (
                <img
                  src={imagePreview}
                  alt="Preview room photo"
                  style={{ maxWidth: '400px', maxHeight: '400px' }}
                  className="mb-3"
                />
              )}
            </div>
            <div className="d-grid justify-content-between d-md-flex mt-2">
              <button type="button" className="btn btn-outline-primary ml-5" onClick={handleBack}>Back</button>
              <button className="btn btn-outline-primary">Save Room</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};

export default AddRoom;
