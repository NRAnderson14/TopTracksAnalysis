import { Component } from "react";
import { Link } from "react-router-dom";
import ArtistGraph from "./ArtistGraph";
import ArtistList from "./ArtistList";
import TrendGraph from "./TrendGraph";

class ArtistAnalysis extends Component {

  render() {
    return (
      <div id="landing">
        <h1>Top Tracks Analysis<span className="back-button"><Link to="/">{"◀ Back"}</Link></span></h1>
        <ArtistGraph />
        <ArtistList />
      </div>
    );
  }
}

export default ArtistAnalysis;