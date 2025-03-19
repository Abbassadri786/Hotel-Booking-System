import React from "react";
import { Container } from "react-bootstrap";

const Parallax2 = () => {
  return (
    <div className="parallax2 mb-5">
      <Container className="text-center px-5 py-5 justify-content-center">
        <div className="animated-texts bounceIn">
          <h1>
            Discover <span className="hotel-color">Paradise on Earth</span>
          </h1>
          <h3>
            Nestled in the heart of serene landscapes, our hotel offers a
            tranquil escape from the everyday hustle. Indulge in panoramic views
             and gourmet experiences that celebrate
            local flavors. Whether for relaxation or adventure, the perfect
            getaway awaits.
          </h3>
        </div>
      </Container>
    </div>
  );
};

export default Parallax2;
