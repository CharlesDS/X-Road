/*
 * The MIT License
 * Copyright (c) 2019- Nordic Institute for Interoperability Solutions (NIIS)
 * Copyright (c) 2018 Estonian Information System Authority (RIA),
 * Nordic Institute for Interoperability Solutions (NIIS), Population Register Centre (VRK)
 * Copyright (c) 2015-2017 Estonian Information System Authority (RIA), Population Register Centre (VRK)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import axios from 'axios';
import { ActionTree, GetterTree, Module, MutationTree } from 'vuex';
import { RootState, StoreTypes } from '@/global';
import {
  CentralServerAddress,
  SystemStatus,
  TokenInitStatus,
  Version,
} from '@/openapi-types';
import * as api from '@/util/api';

export interface State {
  serverVersion: Version | undefined;
  systemStatus: SystemStatus | undefined;
}

export const getDefaultState = (): State => {
  return {
    serverVersion: undefined,
    systemStatus: {
      initialization_status: {
        instance_identifier: '',
        central_server_address: '',
        software_token_init_status: TokenInitStatus.UNKNOWN,
      },
      high_availability_status: {
        is_ha_configured: false,
        node_name: undefined,
      },
    },
  };
};

// Initial state. The state can be reset with this.
const moduleState = getDefaultState();

// noinspection JSUnusedLocalSymbols
export const getters: GetterTree<State, RootState> = {
  [StoreTypes.getters.SERVER_VERSION](state) {
    return state.serverVersion;
  },
  [StoreTypes.getters.SYSTEM_STATUS](state) {
    return state.systemStatus;
  },
};

export const mutations: MutationTree<State> = {
  [StoreTypes.mutations.SET_SERVER_VERSION]: (state, version: Version) => {
    state.serverVersion = version;
  },
  [StoreTypes.mutations.SET_SYSTEM_STATUS]: (
    state,
    systemStatus: SystemStatus,
  ) => {
    state.systemStatus = systemStatus;
  },
};

export const actions: ActionTree<State, RootState> = {
  async [StoreTypes.actions.FETCH_SERVER_VERSION]({ commit }) {
    return axios
      .get<Version>('/system/version')
      .then((resp) =>
        commit(StoreTypes.mutations.SET_SERVER_VERSION, resp.data),
      );
  },
  async [StoreTypes.actions.FETCH_SYSTEM_STATUS]({ commit }) {
    return api
      .get<Version>('/system/status')
      .then((resp) =>
        commit(StoreTypes.mutations.SET_SYSTEM_STATUS, resp.data),
      );
  },
  async [StoreTypes.actions.UPDATE_CENTRAL_SERVER_ADDRESS](
    { commit },
    newAddress: CentralServerAddress,
  ) {
    return api
      .put<SystemStatus>('/system/server-address', newAddress)
      .then((resp) =>
        commit(StoreTypes.mutations.SET_SYSTEM_STATUS, resp.data),
      );
  },
};

export const module: Module<State, RootState> = {
  namespaced: false,
  state: moduleState,
  getters,
  actions,
  mutations,
};
