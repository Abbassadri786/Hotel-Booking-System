import React from 'react';
import { Container } from 'react-bootstrap';

const PrivacyPolicy = () => {
  return (
    <Container className="mt-5 mb-5">
      <h2 className="text-center">Privacy Policy</h2>
      <p>
        At <strong>The Amber Lotus</strong>, we are committed to protecting your privacy and ensuring the safety of your personal data. Our privacy policy explains how we collect, use, and safeguard the information you provide to us.
      </p>
      <h5>Information We Collect</h5>
      <p>
        We collect personal data such as your name, email, and phone number when you book with us. This information is used solely for providing our services and improving your experience.
      </p>
      <h5>How We Use Your Data</h5>
      <p>
        Your data is used to process bookings, provide customer service, and ensure a seamless experience. We do not share your data with third parties without your consent.
      </p>
      <p>
        For more details about our policies, feel free to contact our customer care team.
      </p>
    </Container>
  );
};

export default PrivacyPolicy;
