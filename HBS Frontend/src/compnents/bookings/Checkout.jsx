import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getRoomById } from "../utils/ApiFunctions";
import BookingForm from "./BookingForm";
import RoomCarousel from "../common/RoomCarousel";
import {FaUtensils,FaWifi,FaTv,FaWineGlassAlt,FaParking,FaCar,FaTshirt,FaArrowLeft} from "react-icons/fa";

const Checkout = () => {
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [roomInfo, setRoomInfo] = useState({
    photo: "",
    roomType: "",
    roomPrice: "",
  });

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    setTimeout(() => {
      getRoomById(id)
        .then((response) => {
          setRoomInfo(response);
          setIsLoading(false);
        })
        .catch((error) => {
          setError(error.message || "Failed to fetch room information.");
          setIsLoading(false);
        });
    }, 2000);
  }, [id]);

  return (
    <div>
      {/* Back Arrow Button */}
      <div className="container mt-3">
        <button
          className="btn btn-link text-decoration-none text-dark"
          onClick={() => navigate("/home")}
          style={{
            display: "flex",
            alignItems: "center",
            gap: "5px",
            fontSize: "16px",
          }}
        >
          <FaArrowLeft /> Back to Home
        </button>
      </div>

      <section className="container">
        <div className="row">
          <div className="col-md-6 mt-5 mb-5">
            {isLoading ? (
              <p>Loading room information...</p>
            ) : error ? (
              <p className="text-danger">{error}</p>
            ) : (
              <div className="room-info">
                <img
                  src={`${roomInfo.photo}`}
                  alt="Room photo"
                  style={{ width: "100%", height: "200px" }}
                />
                <table className="table table-bordered">
                  <tbody>
                    <tr>
                      <th>Room Type:</th>
                      <td>{roomInfo.roomType}</td>
                    </tr>
                    <tr>
                      <th>Price per night:</th>
                      <td>₹ {roomInfo.price}</td>
                    </tr>
                    <tr>
                      <th>Room Service:</th>
                      <td>
                        <ul className="list-unstyled">
                          <li>
                            <FaWifi /> Wifi
                          </li>
                          <li>
                            <FaTv /> Netflix Premium
                          </li>
                          <li>
                            <FaUtensils /> Breakfast
                          </li>
                          <li>
                            <FaWineGlassAlt /> Mini bar refreshment
                          </li>
                          <li>
                            <FaCar /> Car Service
                          </li>
                          <li>
                            <FaParking /> Parking Space
                          </li>
                          <li>
                            <FaTshirt /> Laundry
                          </li>
                        </ul>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            )}
          </div>
          <div className="col-md-8">
            <BookingForm />
          </div>
        </div>
      </section>

      <div className="container mb-5">
        <RoomCarousel />
      </div>

      {/* Feedback Button Section */}
      <div className="container text-center mb-5">
        <button
          className="btn btn-success"
          onClick={() => navigate("/feedback")}
          style={{
            fontSize: "16px",
            padding: "10px 20px",
          }}
        >
          Give Feedback!
        </button>
      </div>
    </div>
  );
};

export default Checkout;
