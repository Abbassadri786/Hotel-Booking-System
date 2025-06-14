import React from "react";
import MainHeader from "../layout/MainHeader";
import HotelService from "../common/HotelService";
import Parallax from "../common/Parallax";
import Parallax2 from "../common/Parallax2";
import RoomCarousel from "../common/RoomCarousel";
import RoomSearch from "../common/RoomSearch";
import FeedbackCarousel from "../feedback/FeedbackCarousel";

const Home = () => {
  return (
    <section>
      <MainHeader />
      <section className="container">
        <RoomSearch />
        <RoomCarousel />
        <Parallax />
        <HotelService />
        <FeedbackCarousel/>
        <Parallax2 />
      </section>
    </section>
  );
};

export default Home;
