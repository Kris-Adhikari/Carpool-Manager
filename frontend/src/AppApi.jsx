import React, { createContext, useContext, useReducer, useEffect } from "react";

const SET_RIDELISTS = "SET_RIDELISTS";
const SET_RIDES = "SET_RIDES";
const ADD_RIDE = "ADD_RIDE";
const UPDATE_RIDE = "UPDATE_RIDE";
const DELETE_RIDE = "DELETE_RIDE";

const initialState = {
  rideLists: [], 
  rides: {},     
};

function reducer(state, action) {
  switch (action.type) {
    case SET_RIDELISTS:
      return {
        ...state,
        rideLists: action.payload,
      };

    case SET_RIDES:
      return {
        ...state,
        rides: {
          ...state.rides,
          [action.payload.rideListId]: action.payload.rides,
        },
        rideLists: state.rideLists.map((list) =>
          list.id === action.payload.rideListId
            ? { ...list, rides: action.payload.rides.length }
            : list
        ),
      };

    case ADD_RIDE:
      return {
        ...state,
        rides: {
          ...state.rides,
          [action.payload.rideListId]: [
            ...(state.rides[action.payload.rideListId] || []),
            action.payload.ride,
          ],
        },
        rideLists: state.rideLists.map((list) =>
          list.id === action.payload.rideListId
            ? { ...list, rides: list.rides + 1 }
            : list
        ),
      };

    case UPDATE_RIDE:
      return {
        ...state,
        rides: {
          ...state.rides,
          [action.payload.rideListId]: (state.rides[action.payload.rideListId] || []).map(
            (ride) =>
              ride.id === action.payload.ride.id ? action.payload.ride : ride
          ),
        },
      };

    case DELETE_RIDE:
      return {
        ...state,
        rides: {
          ...state.rides,
          [action.payload.rideListId]: (state.rides[action.payload.rideListId] || []).filter(
            (ride) => ride.id !== action.payload.rideId
          ),
        },
        rideLists: state.rideLists.map((list) =>
          list.id === action.payload.rideListId
            ? { ...list, rides: list.rides - 1 }
            : list
        ),
      };

    default:
      return state;
  }
}

const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    fetchRideLists().catch((err) => {
      console.error("Error fetching ride lists:", err);
    });
  }, []);

  // api calls
  const fetchRideLists = async () => {
    const res = await fetch("/api/ride-lists");
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(`Failed to fetch ride lists: ${res.status} - ${txt}`);
    }
    const data = await res.json();
    dispatch({ type: SET_RIDELISTS, payload: data });
  };

  const fetchRides = async (rideListId) => {
    const res = await fetch(`/api/ride-lists/${rideListId}/rides`);
    if (!res.ok) {
      const txt = await res.text();
      throw new Error(`Failed to fetch rides: ${res.status} - ${txt}`);
    }
    const data = await res.json();
    dispatch({ type: SET_RIDES, payload: { rideListId, rides: data } });
  };

  const createRide = async (rideListId, rideData) => {
    const res = await fetch(`/api/ride-lists/${rideListId}/rides`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(rideData),
    });
    if (!res.ok) {
      const errTxt = await res.text();
      throw new Error(`Failed to create ride. ${res.status} - ${errTxt}`);
    }
    const data = await res.json();
    dispatch({ type: ADD_RIDE, payload: { rideListId, ride: data } });
    return data;
  };

  const updateRide = async (rideId, rideListId, rideData) => {
    const res = await fetch(`/api/ride-lists/${rideListId}/rides/${rideId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(rideData),
    });
    if (!res.ok) {
      const errTxt = await res.text();
      throw new Error(`Failed to update ride. ${res.status} - ${errTxt}`);
    }
    const data = await res.json();
    dispatch({ type: UPDATE_RIDE, payload: { rideListId, ride: data } });
    return data;
  };

  const deleteRide = async (rideId, rideListId) => {
    const res = await fetch(`/api/ride-lists/${rideListId}/rides/${rideId}`, {
      method: "DELETE",
    });
    if (!res.ok) {
      const errTxt = await res.text();
      throw new Error(`Failed to delete ride. ${res.status} - ${errTxt}`);
    }
    dispatch({ type: DELETE_RIDE, payload: { rideListId, rideId } });
  };

  const api = {
    fetchRideLists,
    fetchRides,
    createRide,
    updateRide,
    deleteRide,
  };

  return (
    <AppContext.Provider value={{ state, api }}>
      {children}
    </AppContext.Provider>
  );
};

export const useAppContext = () => {
  const ctx = useContext(AppContext);
  if (!ctx) {
    throw new Error("useAppContext must be used within an AppProvider");
  }
  return ctx;
};
