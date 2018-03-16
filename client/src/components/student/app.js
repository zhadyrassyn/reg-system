import React, { Component } from 'react'
import "./overview_menu"
import OverviewMenu from "./overview_menu";

class StudentApp extends Component {
  render() {
    return (
      <div>
        Hello, student app
        <OverviewMenu/>
      </div>
    )
  }
}

export default StudentApp