/*
 * Copyright (C) 2013 Lord_Ralex
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.ae97.teamstats.network.api;

/**
 * @version 1.0
 * @author Lord_Ralex
 */
public enum Request {

    SEND_LOGIN,
    LOGIN_SUCCESSFUL,
    LOGIN_FAILED,
    GET_STATS,
    SEND_STATS,
    GET_FRIENDS,
    ADD_FRIEND,
    REMOVE_FRIEND,
    GET_NEW_FRIENDS,
    GET_REQUESTS,
    DENY_REQUEST,
    GET_NEW_REQUESTS,
    DISCONNECT,
    GET_VERSION
}
