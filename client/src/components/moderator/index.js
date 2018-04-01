import React, { Component } from "react"
import { bindActionCreators } from "redux"
import { connect } from "react-redux"
import SearchBar from "./search_bar"
import TableHeader from "./table_header"
import TableBody from "./table_body"

import {
  fetchStudents
} from "../../actions"

class ModeratorApp extends Component {

  componentWillMount() {
    document.body.style.backgroundColor = "#F8F6F9";
  }
  componentWillUnmount() {
    document.body.style.backgroundColor = null;
  }

  componentDidMount() {
    this.props.fetchStudents()
  }

  render() {
    const {students} = this.props

    return (
      <div className="wrapper">
        <SearchBar/>
        <TableHeader/>
        <TableBody/>
      </div>
    )
  }
}

export default connect(
  state => ({
    students: state.moderator.students
  }),
  dispatch => ({
    fetchStudents: bindActionCreators(fetchStudents, dispatch)
  })
)(ModeratorApp)