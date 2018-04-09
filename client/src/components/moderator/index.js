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
    console.log(localStorage.getItem('token'))
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
            <div className="row">
              <div className="col-md-12">
                <button onClick={this.closeModal} className="close" type="button"/>
                <h3>General info</h3>
                <p>Daniyar Temirbekovich Zhadyrassyn, 11/06/1997. Status: Accept</p>
                <p>Город: Алматы. Школа: 3</p>
                <p>Email: zhadyrassyn.daniyar@is.sdu.edu.kz</p>
                <div className="form-group">
                  <label htmlFor="generalInfoComment"><strong>Leave comment</strong></label>
                  <textarea className="form-control" id="generalInfoComment" rows="3" placeholder="Leave comment..."/>
                </div>
                <div>
                  <button className="btn btn-outline-success width120">Accept</button>
                  <button className="btn btn-outline-danger width120 ml-2">Reject</button>
                </div>
              </div>
            </div>

            <div className="row mt-3">
              <div className="col-md-12">
                <h3>Documents</h3>
                <ol>
                  <li>
                    <span>Document</span>
                    <div className="form-check form-check-inline ml-2">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                      </div>
                    </div>
                  </li>
                  <li>
                    <span>Document</span>
                    <div className="form-check form-check-inline ml-2">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                      </div>
                    </div>
                  </li>
                  <li>
                    <span>Document</span>
                    <div className="form-check form-check-inline ml-2">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                      </div>
                    </div>
                  </li>
                  <li>
                    <span>Document</span>
                    <div className="form-check form-check-inline ml-2">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                      </div>
                    </div>
                  </li>
                  <li>
                    <span>Document</span>
                    <div className="form-check form-check-inline ml-2">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                      </div>
                    </div>
                  </li>
                  <li>
                    <span><a href="#" target="_blank">Document</a></span>
                    <div className="form-check form-check-inline ml-2">
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Accepted</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1"/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Rejected</label>
                      </div>
                      <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked/>
                        <label className="form-check-label" htmlFor="inlineRadio1">Waiting for response</label>
                      </div>
                    </div>
                  </li>
                </ol>
                <div className="form-group">
                  <label htmlFor="documentsComment"><strong>Leave comment</strong></label>
                  <textarea className="form-control" id="documentsComment" rows="3" placeholder="Leave comment..."/>
                </div>
                <div>
                  <button className="btn btn-outline-success width120">Accept</button>
                  <button className="btn btn-outline-danger width120 ml-2">Reject</button>
                </div>
              </div>
            </div>
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