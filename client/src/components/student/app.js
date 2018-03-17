import React, { Component } from "react"
import "./overview_menu"
import OverviewMenu from "./overview_menu"
import Select from "react-select"
import {connect} from "react-redux"
import {bindActionCreators} from "redux"

import {
  fetchCities,
  fetchSchools
} from "../../actions"

class StudentApp extends Component {
  constructor(props) {
    super(props)

    this.state = {
      city: '',
      school: '',
      schoolSelectDisabled: true
    }
  }

  componentDidMount() {
    const { fetchCities } = this.props
    fetchCities()
  }

  handleCityChange = (city) => {
    this.setState({ city }, () => {
      const {fetchSchools} = this.props
      const {city} = this.state

      if(city) {
        fetchSchools(this.state.city.value,
          () => {
            this.setState({
              school: '',
              schoolSelectDisabled: false
            })
          },
          () => {
            console.log('error on fetching schools')
          })
      } else {
        this.setState({schoolSelectDisabled: true})
      }

    })
    console.log(`Selected: ${city.label}`);
  }

  handleSchoolChange = (school) => {
    this.setState({ school })
  }

  render() {
    const { city, school, schoolSelectDisabled } = this.state
    const { cities } = this.props
    const { schools } = this.props

    const cityValue = city && city.value
    const schoolValue = school && school.value

    return (
      <div className="container">
        <OverviewMenu/>
        <form>
          <div className="form-row">
            <div className="col">
              <label htmlFor="firstName">First name</label>
              <input type="text" className="form-control" placeholder="First name" id="firstName"/>
            </div>
            <div className="col">
              <label htmlFor="middleName">Middle name</label>
              <input type="text" className="form-control" placeholder="Middle name" id="middleName"/>
            </div>
            <div className="col">
              <label htmlFor="lastName">Last name</label>
              <input type="text" className="form-control" placeholder="Last name" id="lastName"/>
            </div>
          </div>
          <div className="form-row mt-3">
            <div className="col">
              <label htmlFor="birthDate">Date of birth</label>
              <input type="date" className="form-control" id="birthDate"/>
            </div>
          </div>
          <div className="form-row mt-3">
            <div className="col">
              <label htmlFor="city">City</label>
              <Select
                name="city"
                value={cityValue}
                onChange={this.handleCityChange}
                options={cities}
              />
            </div>
          </div>
          <div className="form-row mt-3">
            <div className="col">
              <label htmlFor="school">School</label>
              <Select
                value={schoolValue}
                disabled={schoolSelectDisabled}
                name="school"
                onChange={this.handleSchoolChange}
                options={schools}
              />
            </div>
            <div className="col">
              <label htmlFor="anotherSchool">Not finding your school? Write down</label>
              <input type="text" className="form-control" disabled={schoolSelectDisabled}/>
            </div>
          </div>
          <div className="col text-right">
            <button className="btn btn-success mt-3" type="submit">Save</button>
          </div>
        </form>
      </div>
    )
  }
}

export default connect(
  state => ({
    cities: state.info.cities,
    schools: state.info.schools
  }),
  dispatch => ({
    fetchCities: bindActionCreators(fetchCities, dispatch),
    fetchSchools: bindActionCreators(fetchSchools, dispatch)
  })
)(StudentApp)