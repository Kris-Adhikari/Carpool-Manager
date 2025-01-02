import React, { useState } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  Divider,
  Button,
  Progress,
  CheckboxGroup,
  Checkbox,
} from "@nextui-org/react";
import { useNavigate } from "react-router-dom";
import { useAppContext } from "../AppApi";

const RideListsPage = () => {
  const navigate = useNavigate();
  const { state } = useAppContext();

  // selecting the ridelist ids
  const [selectedCommunities, setSelectedCommunities] = useState(
    state.rideLists.map((rideList) => rideList.id)
  );

  // filtering the rides
  const filteredRideLists = state.rideLists.filter((rideList) =>
    selectedCommunities.includes(rideList.id)
  );

  // viewing the rides
  const handleViewRides = (rideListId) => {
    navigate(`/ride-lists/${rideListId}/rides`);
  };

  return (
    <div className="p-5 h-screen bg-gray-900 text-white flex flex-col">
      <h1 className="text-5xl font-bold text-left mb-5">RideBuddy</h1>

      <div className="flex flex-grow gap-5 overflow-hidden">
        <Card className="max-w-xs bg-gray-800 flex flex-col">
          <CardHeader>
            <h2 className="text-xl font-semibold">Filter by Community</h2>
          </CardHeader>
          <Divider />
          <CardBody className="flex-grow overflow-auto">
            <CheckboxGroup
              value={selectedCommunities}
              label="Select communities"
              onChange={(values) => setSelectedCommunities(values)}
              className="space-y-2"
            >
              {state.rideLists.map((rideList) => (
                <Checkbox key={rideList.id} value={rideList.id}>
                  {rideList.community}
                </Checkbox>
              ))}
            </CheckboxGroup>
          </CardBody>
        </Card>

        <Card className="flex-grow bg-gray-800 flex flex-col">
          <CardHeader>
            <h2 className="text-2xl font-semibold">Communities</h2>
          </CardHeader>
          <Divider />
          <div className="flex-grow overflow-auto p-4">
            <div className="flex flex-col gap-4">
              {filteredRideLists.map((community) => (
                <Card key={community.id} className="w-full bg-gray-700 text-white">
                  <CardBody>
                    <div className="flex flex-col">
                      <h3 className="text-lg font-semibold">
                        {community.community}
                      </h3>
                      <p className="text-sm mt-2">
                        {community.rides} rides available
                      </p>
                      <Progress
                        value={Math.min((community.rides / 10) * 100, 100)}
                        className="mt-2"
                        color="primary"
                        aria-label={`Ride progress for ${community.community}`}
                      />
                    </div>
                  </CardBody>
                  <Divider />
                  <div className="flex justify-between p-2">
                    <Button
                      size="sm"
                      color="primary"
                      onClick={() => handleViewRides(community.id)}
                    >
                      View Rides
                    </Button>
                    <Button size="sm" color="secondary">
                      More Info
                    </Button>
                  </div>
                </Card>
              ))}
            </div>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default RideListsPage;
