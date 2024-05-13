import React, { useState } from "react";

const OTPVerificationPage = () => {
  const [otp, setOTP] = useState("");

  const handleOTPChange = (e) => {
    const input = e.target.value;
    // Allow only numerical characters
    const regex = /^[0-9]*$/;
    if (regex.test(input)) {
      setOTP(input);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you can add logic to submit the OTP for verification
    console.log("Submitting OTP:", otp);
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <form onSubmit={handleSubmit} className="max-w-sm w-full">
        <div className="mb-4">
          <label htmlFor="otp" className="block text-gray-700 text-sm font-bold mb-2">
            Enter OTP
          </label>
          <input
            type="text"
            id="otp"
            name="otp"
            value={otp}
            onChange={handleOTPChange}
            placeholder="Enter OTP"
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          />
        </div>
        <div className="flex items-center justify-between">
          <button
            type="submit"
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          >
            Submit
          </button>
        </div>
      </form>
    </div>
  );
};

export default OTPVerificationPage;
