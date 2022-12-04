import React from 'react';
import { Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';

const TrendGraph = (props) => {
  const lineColors = [
    "#ffd800",
    "#1c5cef",
    "#e24a69",
    "#1d263a",
    "#ab009a",
    "#00a18c",
    "#d8418a",
    "#1d3fb2",
    "#9e97b2",
    "#e76237"
  ]

  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart data={props.data.graph_data} width={500} height={300} margin={{
        top: 5,
        right: 30,
        left: 20,
        bottom: 5,
      }}>
        <XAxis dataKey="year" />
        <YAxis />
        {props.data.all_data_points.map((dataPoint, index) =>
          <Line type="monotone" dataKey={dataPoint} stroke={lineColors[index]} />
        )}
        <Tooltip itemSorter={(item) => (item.value) * -1} />
        <Legend />
      </LineChart>
    </ResponsiveContainer>
  )
}

export default TrendGraph;