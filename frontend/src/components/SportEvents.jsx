import React, {useState, useEffect} from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const SportEvents = () => {
  const [sportEvents, setSportEvents] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    getSportEvents()
  }, [])

  const getSportEvents = () => {
    axios({
      method: "get",
      url: "http://localhost:8080/api/sportEvent/all",
    }).then((response) => {
      setSportEvents(response.data);
    }).catch((error) => {
      console.log(error);
    });
  };

  const handleEventClick = (sportEventId) => {
    navigate(`/report/${sportEventId}`);
  };

  return (
    <div className="sport-events-container">
      <h2>Sport Events</h2>
      <ul>
        {sportEvents.map((event) => (
          <li key={event.sportEventId} onClick={() => handleEventClick(event.sportEventId)}>
            {event.eventName}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default SportEvents;