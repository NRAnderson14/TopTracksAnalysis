import { Component } from "react";
import { Link } from "react-router-dom";
import TrackList from "./TrackList";

class TrackAnalysis extends Component {

  render() {
    return (
      <div id="landing">
        <h1>Top Tracks Analysis<span className="back-button"><Link to="/">{"◀ Back"}</Link></span></h1>
        <TrackList />
      </div>
    );
  }
}

export default TrackAnalysis;