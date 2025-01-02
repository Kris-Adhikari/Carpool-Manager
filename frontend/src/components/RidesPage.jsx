import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "@nextui-org/react";
import { useAppContext } from "../AppApi";

const RidesPage = () => {
  const { rideListId } = useParams();
  const navigate = useNavigate();
  const { state, api } = useAppContext();

  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  const rideList = state.rideLists.find((rl) => rl.id === rideListId);
  const rides = state.rides[rideListId] || [];

  useEffect(() => {
    if (!rideListId) {
      setError("No rideListId provided in URL");
      setIsLoading(false);
      return;
    }

    // fetch the ride ids for that community (aka ridelistid)
    const loadRides = async () => {
      try {
        setIsLoading(true);
        await api.fetchRides(rideListId);
      } catch (err) {
        setError(err.message || "Unknown error fetching rides");
      } finally {
        setIsLoading(false);
      }
    };

    loadRides();
  }, [rideListId, api]);

  const handleBack = () => {
    navigate("/");
  };

  const handleCreateRide = () => {
    navigate(`/ride-lists/${rideListId}/new-ride`);
  };

  const handleEditRide = (rId) => {
    navigate(`/ride-lists/${rideListId}/edit-ride/${rId}`);
  };

  if (isLoading) {
    return (
      <div className="p-5 h-screen bg-gray-900 text-white flex flex-col items-center justify-center">
        <p>Loading rides...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="p-5 h-screen bg-gray-900 text-white flex flex-col items-center justify-center">
        <p className="text-red-500">{error}</p>
        <Button color="secondary" onClick={handleBack} className="mt-4">
          Go Back
        </Button>
      </div>
    );
  }

  if (!rideList) {
    return (
      <div className="p-5 h-screen bg-gray-900 text-white flex flex-col items-center justify-center">
        <p>Invalid Ride List ID: {rideListId}</p>
        <Button color="secondary" onClick={handleBack} className="mt-4">
          Go Back
        </Button>
      </div>
    );
  }

  return (
    <div className="p-5 h-screen bg-gray-900 text-white flex flex-col items-center">
      <h1 className="text-5xl font-bold mb-5">RideBuddy</h1>

      <Button color="secondary" onClick={handleBack} className="mb-5 w-32">
        Back
      </Button>

      <h2 className="text-2xl font-semibold mb-4">
        Rides for {rideList.community}
      </h2>

      <Button color="primary" onClick={handleCreateRide} className="mb-4">
        Create New Ride
      </Button>

      {rides.length === 0 ? (
        <p>No rides found.</p>
      ) : (
        rides.map((ride) => (
          <div
            key={ride.id}
            className="w-full bg-gray-700 p-4 mb-4 text-white rounded"
          >
            <p><strong>Car:</strong> {ride.car}</p>
            <p><strong>Contact:</strong> {ride.contact}</p>
            <p><strong>Time:</strong> {ride.time}</p>
            <p><strong>Status:</strong> {ride.status}</p>
            <p><strong>Departure:</strong> {ride.departure}</p>
            <Button
              size="sm"
              color="primary"
              onClick={() => handleEditRide(ride.id)}
              className="mt-2"
            >
              Edit
            </Button>
          </div>
        ))
      )}
    </div>
  );
};

export default RidesPage;
