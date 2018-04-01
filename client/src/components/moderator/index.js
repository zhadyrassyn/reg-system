import React, { Component } from "react"
import { bindActionCreators } from "redux"
import { connect } from "react-redux"
import SearchBar from "./search_bar"
import TableHeader from "./table_header"
import TableBody from "./table_body"
import Pagination from "./pagination"

import {
  fetchStudents
} from "../../actions"

class ModeratorApp extends Component {

  constructor(props) {
    super(props)

    this.state = {
      currentPage: 1,
      perPage: 10
    }
  }

  componentWillMount() {
    document.body.style.backgroundColor = "#F8F6F9";
  }
  componentWillUnmount() {
    document.body.style.backgroundColor = null;
  }

  componentDidMount() {
    const { currentPage, perPage } = this.state
    this.props.fetchStudents(currentPage, perPage)
  }

  handlePageChangeClick = (pageNum) => {
    this.props.fetchStudents(pageNum, this.state.perPage,
      () => {
        this.setState({ currentPage: pageNum })
      })
  }

  render() {
    const { students } = this.props
    const { currentPage, perPage } = this.state
    const startCounter = (currentPage-1) * perPage

    return (
      <div className="wrapper">
        <SearchBar/>
        <TableHeader/>
        <TableBody students={ students } startCounter={ startCounter }/>
        <Pagination currentPage={ currentPage } perPage={ perPage } handlePageChangeClick={this.handlePageChangeClick}/>
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