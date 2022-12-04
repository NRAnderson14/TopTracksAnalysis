import React, { Component, useState } from 'react';
import TrendGraph from './TrendGraph';

class ArtistGraph extends Component {
  state = {
    isLoading: true,
    graphData: {}
  };

  async componentDidMount() {
    const response = await fetch("/user/playlists/recaps/graph_data?type=artist&num_to_graph=10");
    const body = await response.json();
    this.setState({ graphData: body, isLoading: false });
  }

  render() {
    return this.state.isLoading ? null :
      (
        <TrendGraph data={this.state.graphData} />
      )
  }
}

export default ArtistGraph;