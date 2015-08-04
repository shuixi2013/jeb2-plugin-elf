/*
 * JEB Copyright PNF Software, Inc.
 * 
 *     https://www.pnfsoftware.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pnf.plugin.elf.internal;

public class NameSectionHeader extends SectionHeader {

    public NameSectionHeader(byte[] data, int size, int offset) {
        super(data, size, offset, ".shstrtab");
    }

    public String getSectionName(int index) {
        if(section == null) {
            return "";
        }
        return ((StringTableSection)section).getString(index);
    }

    public int getTableOffset() {
        return section.getOffset();
    }
}
