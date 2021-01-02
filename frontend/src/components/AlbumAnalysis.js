import { Component } from "react";
import { Link } from "react-router-dom";
import AlbumList from "./AlbumList";

class AlbumAnalysis extends Component {

  render() {
    return (
      <div id="landing">
        <h1>Top Tracks Analysis<span className="back-button"><Link to="/">{"◀Back"}</Link></span></h1>
        <AlbumList />
      </div>
    );
  }
}

export default AlbumAnalysis;