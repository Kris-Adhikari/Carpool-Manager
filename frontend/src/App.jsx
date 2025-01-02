import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { NextUIProvider } from "@nextui-org/react";
import { AppProvider } from "./AppApi";

import RideListsPage from "./components/RideListsPage";
import RidesPage from "./components/RidesPage";
import CrudRidesScreen from "./components/CrudRidesScreen";

function App() {
  return (
    <NextUIProvider>
      <AppProvider>
        <Router>
          <Routes>
            <Route path="/" element={<RideListsPage />} />
            <Route path="/ride-lists/:rideListId/rides" element={<RidesPage />} />
            <Route path="/ride-lists/:rideListId/new-ride" element={<CrudRidesScreen />} />
            <Route path="/ride-lists/:rideListId/edit-ride/:rideId" element={<CrudRidesScreen />} />
          </Routes>
        </Router>
      </AppProvider>
    </NextUIProvider>
  );
}

export default App;
