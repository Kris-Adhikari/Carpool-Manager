import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  Button,
  Card,
  CardBody,
  Chip,
  Input,
  Spacer,
  Spinner,
} from "@nextui-org/react";
import { DatePicker } from "@nextui-org/date-picker";
import { parseDate } from "@internationalized/date";
import { useAppContext } from "../AppApi";

const RideTimeOptions = ["MORNING", "AFTERNOON", "EVENING"];
const RideStatusOptions = ["OPEN", "FILLED"];

const CrudRidesScreen = () => {
  const { rideListId, rideId } = useParams();
  const navigate = useNavigate();
  const { state, api } = useAppContext();

  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");
  const [isUpdate, setIsUpdate] = useState(false);

  // Ride fields
  const [car, setCar] = useState("");
  const [contact, setContact] = useState("");
  const [time, setTime] = useState("MORNING");
  const [status, setStatus] = useState("OPEN");
  const [departureDate, setDepartureDate] = useState(null);

  const isRideListValid = state.rideLists.some((rl) => rl.id === rideListId);

  useEffect(() => {
    if (!rideListId || !isRideListValid) {
      setError(`Invalid Ride List ID: ${rideListId}`);
      setIsLoading(false);
      return;
    }

    if (!rideId) {
      setIsUpdate(false);
      setIsLoading(false);
      return;
    }

    setIsUpdate(true);
    setIsLoading(true);

    (async () => {
      try {
        const res = await fetch(`/api/ride-lists/${rideListId}/rides/${rideId}`);
        if (!res.ok) {
          const txt = await res.text();
          throw new Error(`Failed to load ride. ${res.status} - ${txt}`);
        }
        const data = await res.json();
        setCar(data.car || "");
        setContact(data.contact || "");
        setTime((data.time || "MORNING").toUpperCase());
        setStatus((data.status || "OPEN").toUpperCase());
        if (data.departure) {
          const dt = new Date(data.departure);
          if (!isNaN(dt.getTime())) setDepartureDate(dt);
        }
      } catch (err) {
        console.error("Error loading ride:", err);
        setError(err.message || "Unknown error.");
      } finally {
        setIsLoading(false);
      }
    })();
  }, [rideListId, rideId, isRideListValid]);

  // next ui stuff
  const formatForDatePicker = (jsDate) => {
    if (!jsDate) return undefined;
    return parseDate(jsDate.toISOString().split("T")[0]);
  };

  const handleDateChange = (val) => {
    if (!val) {
      setDepartureDate(null);
      return;
    }
    const iso = val.toString();
    const newDate = new Date(`${iso}T00:00:00`);
    if (!isNaN(newDate.getTime())) {
      setDepartureDate(newDate);
    } else {
      setDepartureDate(null);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!car.trim()) {
      setError("Car is required.");
      return;
    }
    if (!contact.trim()) {
      setError("Contact is required.");
      return;
    }

    let finalDeparture = null;
    if (departureDate) {
      finalDeparture = departureDate.toISOString().replace("Z", "");
    }

    const rideData = {
      car: car.trim(),
      contact: contact.trim(),
      time,
      status,
      departure: finalDeparture,
    };

    setIsLoading(true);
    try {
      if (isUpdate && rideId) {
        rideData.id = rideId;
        await api.updateRide(rideId, rideListId, rideData);
      } else {
        await api.createRide(rideListId, rideData);
      }
      navigate(`/ride-lists/${rideListId}/rides`);
    } catch (err) {
      console.error("Error saving ride:", err);
      setError(err.message || "Failed to save ride.");
    } finally {
      setIsLoading(false);
    }
  };

  const handleBack = () => {
    navigate(`/ride-lists/${rideListId}/rides`);
  };

  if (!isRideListValid) {
    return (
      <div className="p-4 text-red-500">
        Invalid Ride List ID: {rideListId}
      </div>
    );
  }

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <Spinner />
      </div>
    );
  }

  return (
    <div className="p-4 max-w-md mx-auto">
      <div className="flex items-center space-x-4 mb-6">
        <Button variant="light" onPress={handleBack}>
          Back
        </Button>
        <h1 className="text-2xl font-bold">
          {isUpdate ? "Update Ride" : "Create Ride"}
        </h1>
      </div>

      {error && (
        <Card color="danger" className="mb-4 p-4">
          <CardBody>
            <div className="text-red-500">{error}</div>
          </CardBody>
        </Card>
      )}

      <form onSubmit={handleSubmit}>
        <Input
          label="Car"
          placeholder="Car model"
          value={car}
          onChange={(e) => setCar(e.target.value)}
          required
          fullWidth
        />
        <Spacer y={1} />

        <Input
          label="Contact"
          placeholder="Phone/email"
          value={contact}
          onChange={(e) => setContact(e.target.value)}
          required
          fullWidth
        />
        <Spacer y={1} />

        <DatePicker
          label="Departure"
          selected={formatForDatePicker(departureDate)}
          onChange={handleDateChange}
        />
        <Spacer y={2} />

        <div className="flex flex-wrap gap-2 mb-2">
          {RideTimeOptions.map((t) => (
            <Chip
              key={t}
              variant={time === t ? "solid" : "ghost"}
              color={time === t ? "primary" : "default"}
              onClick={() => setTime(t)}
            >
              {t}
            </Chip>
          ))}
        </div>

        <div className="flex flex-wrap gap-2 mb-2">
          {RideStatusOptions.map((s) => (
            <Chip
              key={s}
              variant={status === s ? "solid" : "ghost"}
              color={status === s ? "secondary" : "default"}
              onClick={() => setStatus(s)}
            >
              {s}
            </Chip>
          ))}
        </div>

        <Spacer y={2} />

        <Button type="submit" color="primary" fullWidth disabled={isLoading}>
          {isUpdate ? "Update Ride" : "Create Ride"}
        </Button>
      </form>
    </div>
  );
};

export default CrudRidesScreen;
