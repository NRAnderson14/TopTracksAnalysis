import { Component } from "react";
import { Link } from "react-router-dom";
import ArtistList from "./ArtistList";

class ArtistAnalysis extends Component {

  render() {
    return (
      <div id="landing">
        <h1>Top Tracks Analysis<span className="back-button"><Link to="/">{"◀Back"}</Link></span></h1>
        <ArtistList />
      </div>
    );
  }
}

export default ArtistAnalysis;