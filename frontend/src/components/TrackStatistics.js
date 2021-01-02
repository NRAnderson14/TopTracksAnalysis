const { Component } = require("react");

class TrackStatistics extends Component {

  render() {
    return (
      <div className="track-statistics">
        <div className="stat">
          <p>Occurrences: {this.props.stats.occurrences}</p>
        </div>
        <div className="stat">
          <p>First Appearance: {this.props.dateFormatter(this.props.stats.firstAppearance)}</p>
          <p>Latest Appearance: {this.props.dateFormatter(this.props.stats.latestAppearance)}</p>
        </div>
        <div className="stat">
          <p>All Appearances</p>
          <ul>
            {this.props.stats.appearances.map(appearance =>
              <li>{appearance}</li>
            )}
          </ul>
        </div>
        <div className="stat">
          <p>Average Position: {this.props.stats.averagePosition}</p>
        </div>
      </div>
    );
  }
}

export default TrackStatistics;