import React, { Component } from 'react'
import "./overview_menu"
import OverviewMenu from "./overview_menu";

class StudentApp extends Component {
  render() {
    return (
      <div className="container">
        <OverviewMenu/>
        Hello, student app
      </div>
    )
  }
}

export default StudentApp