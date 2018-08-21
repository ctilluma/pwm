/*
 * Password Management Servlets (PWM)
 * http://www.pwm-project.org
 *
 * Copyright (c) 2006-2009 Novell, Inc.
 * Copyright (c) 2009-2018 The PWM Project
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package password.pwm.config.profile;

import password.pwm.config.Configuration;
import password.pwm.config.PwmSetting;
import password.pwm.config.StoredValue;
import password.pwm.config.stored.StoredConfiguration;
import password.pwm.util.java.TimeDuration;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateProfileProfile extends AbstractProfile implements Profile
{

    private static final ProfileType PROFILE_TYPE = ProfileType.UpdateAttributes;

    protected UpdateProfileProfile( final String identifier, final Map<PwmSetting, StoredValue> storedValueMap )
    {
        super( identifier, storedValueMap );
    }

    public static UpdateProfileProfile makeFromStoredConfiguration( final StoredConfiguration storedConfiguration, final String identifier )
    {
        final Map<PwmSetting, StoredValue> valueMap = makeValueMap( storedConfiguration, identifier, PROFILE_TYPE.getCategory() );
        return new UpdateProfileProfile( identifier, valueMap );

    }

    @Override
    public String getDisplayName( final Locale locale )
    {
        return this.getIdentifier();
    }

    @Override
    public ProfileType profileType( )
    {
        return PROFILE_TYPE;
    }

    public TimeDuration getTokenDurationEmail( final Configuration configuration )
    {
        final long duration = readSettingAsLong( PwmSetting.UPDATE_PROFILE_TOKEN_LIFETIME_EMAIL );
        if ( duration < 1 )
        {
            final long defaultDuration = configuration.readSettingAsLong( PwmSetting.TOKEN_LIFETIME );
            return new TimeDuration( defaultDuration, TimeUnit.SECONDS );
        }
        return new TimeDuration( duration, TimeUnit.SECONDS );
    }

    public TimeDuration getTokenDurationSMS( final Configuration configuration )
    {
        final long duration = readSettingAsLong( PwmSetting.UPDATE_PROFILE_TOKEN_LIFETIME_SMS );
        if ( duration < 1 )
        {
            final long defaultDuration = configuration.readSettingAsLong( PwmSetting.TOKEN_LIFETIME );
            return new TimeDuration( defaultDuration, TimeUnit.SECONDS );
        }
        return new TimeDuration( duration, TimeUnit.SECONDS );
    }
}
