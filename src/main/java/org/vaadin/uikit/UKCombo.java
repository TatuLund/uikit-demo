package org.vaadin.uikit;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.vaadin.uikit.UKAlert.AlertHiddenEvent;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.data.binder.HasDataProvider;
import com.vaadin.flow.data.binder.HasItemsAndComponents;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.KeyMapper;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.shared.Registration;

@Tag(Tag.DIV)
public class UKCombo<T> extends SelectBase<UKCombo<T>, T>
		implements HasItemsAndComponents<T>, SingleSelect<UKCombo<T>, T>, Focusable<UKCombo<T>>, HasDataProvider<T>,
		UKValidation, UKTooltip, UKFormSizing, UKMargin, UKPadding {

	private final KeyMapper<T> keyMapper = new KeyMapper<>();

	private DataProvider<T, ?> dataProvider = DataProvider.ofItems();

	private Registration dataProviderListenerRegistration;

	private SerializablePredicate<T> itemEnabledProvider = item -> isEnabled();

	private ItemLabelGenerator<T> itemLabelGenerator = String::valueOf;

	private boolean isReadOnly;

	private UKTextField input = new UKTextField();

	private Element dataList = new Element("datalist");

	private Random random = new Random();

	private String name = "combo-"+random.nextInt();

	private boolean allowCustomValue;

	private static <T> T presentationToModel(UKCombo<T> select, String presentation) {
		if (!select.keyMapper.containsKey(presentation)) {
			return null;
		}
		return select.keyMapper.get(presentation);
	}

	private static <T> String modelToPresentation(UKCombo<T> select, T model) {
		if (!select.keyMapper.has(model)) {
			return null;
		}
		return select.keyMapper.key(model);
	}

	public UKCombo() {
		super(null, null, String.class, UKCombo::presentationToModel, UKCombo::modelToPresentation);
		input.getElement().setAttribute("list", getName());
		dataList.setAttribute("id", getName());
		add(input);
		getElement().appendChild(dataList);
		input.addValueChangeListener(event -> {
			String caption = event.getValue();
			Optional<UKCombo<T>.Option<T>> match = getOptions().filter(option -> option.getValue().equals(caption)).findFirst();
			if (match.isPresent()) {
				getElement().setProperty("value", match.get().getKey());
			} else if (isAllowCustomValue()) {
				fireEvent(new CustomValueEvent<>(this, caption, true));
			}
		});
	}

    public Registration addCustomValueListener(
            ComponentEventListener<CustomValueEvent> listener) {
        return addListener(CustomValueEvent.class, listener);
    }

	public void setAllowCustomValue(boolean allowCustomValue) {
		this.allowCustomValue = allowCustomValue;
	}

	public boolean isAllowCustomValue() {
		return allowCustomValue;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		input.setErrorMessage(errorMessage);
	}
	
	@Override
	public String getErrorMessage() {
		return input.getErrorMessage();
	}

	@Override
	public void setInvalid(boolean invalid) {
		input.setInvalid(invalid);
	}
	
	@Override
	public boolean isInvalid() {
		return input.isInvalid();
	}

	@Override
	public void focus() {
		input.focus();
	}

	@Override
	public void blur() {
		input.blur();
	}

	public void setPlaceholder(String placeholder) {
		input.setPlaceholder(placeholder);
	}

	private void addOption(Option<T> option) {
		dataList.appendChild(option.getElement());
	}

	private void removeAllOptions() {
		dataList.removeAllChildren();
	}

	public SerializablePredicate<T> getItemEnabledProvider() {
		return itemEnabledProvider;
	}

	public void setItemEnabledProvider(SerializablePredicate<T> itemEnabledProvider) {
		this.itemEnabledProvider = Objects.requireNonNull(itemEnabledProvider);
		refreshOptions();
	}

	private void refreshOptions() {
		getOptions().forEach(this::updateOption);
	}

	@SuppressWarnings("unchecked")
	private Stream<Option<T>> getOptions() {
		return getChildren().filter(Option.class::isInstance).map(child -> (Option<T>) child);
	}

	private void updateOption(Option<T> option) {
		String label = getItemLabelGenerator().apply(option.getItem());
		option.setValue(label);
		updateEnabled(option);
	}

	private void updateEnabled(Option<T> option) {
		boolean disabled = isDisabledBoolean() || !getItemEnabledProvider().test(option.getItem());
		Serializable rawValue = option.getElement().getPropertyRaw("disabled");
		if (rawValue instanceof Boolean) {
			// convert the boolean value to a String to force update the
			// property value. Otherwise since the provided value is the same as
			// the current one the update don't do anything.
			option.getElement().setProperty("disabled", disabled ? Boolean.TRUE.toString() : null);
		} else {
			option.setDisabled(disabled);
		}
	}

	@Override
	public void setValue(T value) {
		if (value != null) {
			super.setValue(value);
			input.setValue(itemLabelGenerator.apply(value));
		}
	}

	@Override
	public void onEnabledStateChanged(boolean enabled) {
		if (isReadOnly()) {
			setDisabled(true);
		} else {
			setDisabled(!enabled);
		}
		refreshOptions();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		isReadOnly = readOnly;
		if (isEnabled()) {
			setDisabled(readOnly);
			refreshOptions();
		}
	}

	@Override
	public boolean isReadOnly() {
		return isReadOnly;
	}

	@Override
	public void setDataProvider(DataProvider<T, ?> dataProvider) {
		this.dataProvider = dataProvider;
		reset();

		setupDataProviderListener(dataProvider);
	}

	private void setupDataProviderListener(DataProvider<T, ?> dataProvider) {
		if (dataProviderListenerRegistration != null) {
			dataProviderListenerRegistration.remove();
		}
		dataProviderListenerRegistration = dataProvider.addDataProviderListener(event -> {
			if (event instanceof DataChangeEvent.DataRefreshEvent) {
				resetOption(((DataChangeEvent.DataRefreshEvent<T>) event).getItem());
			} else {
				reset();
			}
		});
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		if (getDataProvider() != null && dataProviderListenerRegistration == null) {
			setupDataProviderListener(getDataProvider());
		}
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		if (dataProviderListenerRegistration != null) {
			dataProviderListenerRegistration.remove();
			dataProviderListenerRegistration = null;
		}
		super.onDetach(detachEvent);
	}

	private void resetOption(T item) {
		getOptions().filter(
				radioButton -> getDataProvider().getId(radioButton.getItem()).equals(getDataProvider().getId(item)))
				.findFirst().ifPresent(this::updateOption);
	}

	private void reset() {
		keyMapper.removeAll();
		removeAllOptions();
		clear();

		getDataProvider().fetch(new Query<>()).map(this::createOption).forEach(this::addOption);
	}

	public void setItemLabelGenerator(ItemLabelGenerator<T> itemLabelGenerator) {
		Objects.requireNonNull(itemLabelGenerator, "The item label generator can not be null");
		this.itemLabelGenerator = itemLabelGenerator;
		reset();
	}

	public ItemLabelGenerator<T> getItemLabelGenerator() {
		return itemLabelGenerator;
	}

	private Option<T> createOption(T item) {
		Option<T> option = new Option<>(keyMapper.key(item), item);
		updateOption(option);
		return option;
	}

	public DataProvider<T, ?> getDataProvider() {
		return dataProvider;
	}

	protected String getName() {
		return name;
	}

	@Tag(Tag.OPTION)
	class Option<R> extends HtmlComponent implements ItemComponent<R> {
		private R item;
		String key;

		Option(String key, R item) {
			this.item = item;
			this.key = key;
		}

		public void setValue(String value) {
			getElement().setProperty("value", value);
		}

		public String getValue() {
			return getElement().getProperty("value", "");
		}

		public String getKey() {
			return key;
		}

		@Override
		public R getItem() {
			return item;
		}

		protected boolean isDisabledBoolean() {
			return getElement().getProperty("disabled", false);
		}

		protected void setDisabled(boolean disabled) {
			getElement().setProperty("disabled", disabled);
		}
	}

    public static class CustomValueEvent<T> extends ComponentEvent<UKCombo<T>> {

    	private String value; 

    	public CustomValueEvent(UKCombo<T> source, String value,
                boolean fromClient) {
            super(source, fromClient);
            this.value = value;
        }

    	public String getValue() {
    		return value;
    	}
    }
}
