import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from "prop-types"

export default function(ComposedComponent) {
  class Authentication extends Component {
    static contextTypes = {
      router: PropTypes.object
    }

    componentWillMount() {
      console.log('Authenticated ', this.props.authenticated)
      if (!this.props.authenticated) {
        this.context.router.push('/registration');
      }
    }

    render() {
      return <ComposedComponent {...this.props} />
    }
  }

  function mapStateToProps(state) {
    return { authenticated: state.auth.authenticated };
  }

  return connect(mapStateToProps)(Authentication);
}