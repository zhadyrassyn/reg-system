import React, { Component } from "react"
import { bindActionCreators } from "redux"
import { connect } from "react-redux"
import SearchBar from "./search_bar"
import TableHeader from "./table_header"
import TableBody from "./table_body"
import Pagination from "./pagination"

import Modal from "react-modal"

const customStyles = {
  content : {
    width: "100%",
    height: "100%",
    top: 0,
    left: 0,
    right: 0,
    bottom: 0
  }
}

Modal.setAppElement(".root")

import {
  fetchStudents
} from "../../actions"

class ModeratorApp extends Component {

  constructor(props) {
    super(props)

    this.state = {
      currentPage: 1,
      perPage: 10,
      modalIsOpen: false
    }

    this.openModal = this.openModal.bind(this);
    this.afterOpenModal = this.afterOpenModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
  }

  openModal() {
    this.setState({modalIsOpen: true});
  }

  afterOpenModal() {
    // references are now sync'd and can be accessed.
    console.log("true modal")
  }

  closeModal() {
    this.setState({modalIsOpen: false});
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
        <TableBody students={ students } startCounter={ startCounter } openModal={this.openModal}/>
        <Pagination currentPage={ currentPage } perPage={ perPage } handlePageChangeClick={this.handlePageChangeClick}/>
        <Modal
          isOpen={this.state.modalIsOpen}
          onAfterOpen={this.afterOpenModal}
          onRequestClose={this.closeModal}
          style={customStyles}
          contentLabel="Example Modal">
          <div className="container">
            <div>
              <button onClick={this.closeModal}>X</button>
            </div>
            <p>Daniyar Temirbekovich Zhadyrassyn, 20 years old</p>
            <p>Город: Алматы. Школа: 3</p>
            <p>Email: zhadyrassyn.daniyar@is.sdu.edu.kz</p>
            <div>
              Documents
              1.UD
              2.bla bla
            </div>
            <button className="btn btn-outline-success">Сохранить</button>
            <button className="btn btn-outline-danger">Отменить</button>
          </div>
        </Modal>
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